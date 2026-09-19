import type { NextPage } from 'next';
import MainLayout from '@/components/layout/MainLayout';
import Hero from '@/components/sections/Hero';
import EventDetails from '@/components/sections/EventDetails';
import GuestBook from '@/components/sections/GuestBook';
import { ThemeProvider } from '@/contexts/ThemeContext';
import CountdownTimer from '@/components/features/CountdownTimer';
import { useEffect, useState } from 'react';
import AOS from 'aos';
// import 'aos/dist/aos.css';
import { useRouter } from 'next/router';
import { InvitationLetter } from '@/types/letter';
import { getDefaultLetter, lookupMockLetter } from '@/services/letter-api';
import { LanguageProvider, useLanguage } from '@/contexts/LanguageContext';

const HomeContent: NextPage = () => {
  const { t } = useLanguage();
  const router = useRouter();
  const [identifier, setIdentifier] = useState('');
  const [letter, setLetter] = useState<InvitationLetter>(getDefaultLetter());
  const [lookupLoading, setLookupLoading] = useState(false);
  const [lookupError, setLookupError] = useState('');

  useEffect(() => {
    if (router.isReady) {
      const guest = router.query.to;
      if (typeof guest === 'string') {
        setIdentifier(decodeURIComponent(guest));
      }
    }
  }, [router.isReady, router.query]);

  useEffect(() => {
    AOS.init({
      duration: 1000,
      once: true,
    });
  }, []);

  const scrollToLetter = () => {
    window.setTimeout(() => {
      document.getElementById('invitation-letter')?.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }, 50);
  };

  const findLetter = async (value: string) => {
    const trimmedValue = value.trim();
    if (!trimmedValue) {
      setLookupError('');
      setLetter(getDefaultLetter());
      scrollToLetter();
      return;
    }

    setLookupLoading(true);
    setLookupError('');

    try {
      if (!process.env.NEXT_PUBLIC_LETTER_API_URL) {
        setLetter(await lookupMockLetter(trimmedValue));
        scrollToLetter();
        return;
      }

      const response = await fetch(process.env.NEXT_PUBLIC_LETTER_API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ identifier: trimmedValue })
      });
      const payload = await response.json();
      if (!response.ok) throw new Error(payload.message || 'We could not find your letter.');

      const result = payload.letter || payload.data || payload;
      const body = typeof result === 'string' ? result : result.body || result.message || result.content;
      if (!body) throw new Error('The letter response is missing its message.');
      setLetter({
        recipient: result.recipient || result.recipientName || result.name || trimmedValue,
        greeting: result.greeting,
        body,
        signature: result.signature
      });
      scrollToLetter();
    } catch {
      setLookupError(t.lookupFallback);
      setLetter(getDefaultLetter());
      scrollToLetter();
    } finally {
      setLookupLoading(false);
    }
  };

  return (
    <ThemeProvider>
      <MainLayout>
        <Hero
          identifier={identifier}
          onIdentifierChange={setIdentifier}
          onLookup={findLetter}
          loading={lookupLoading}
          error={lookupError}
        />
        <CountdownTimer />
        <GuestBook letter={letter} />
        <EventDetails />
      </MainLayout>
    </ThemeProvider>
  );
};

const Home: NextPage = () => (
  <LanguageProvider>
    <HomeContent />
  </LanguageProvider>
);

export default Home;
