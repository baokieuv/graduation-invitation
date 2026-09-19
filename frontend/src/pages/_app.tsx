import '@/styles/globals.css';
import type { AppProps } from 'next/app';
import { Playfair_Display, Montserrat, Great_Vibes } from 'next/font/google';
import { graduationConfig } from '@/config/graduation-config';
import Head from 'next/head';
import { useEffect } from 'react';

const primary = Montserrat({
  subsets: ['latin'],
  weight: ['300', '400', '500', '600', '700'],
  variable: '--font-primary',
  display: 'swap',
});

const secondary = Playfair_Display({
  subsets: ['latin'],
  weight: ['400', '500', '600', '700'],
  variable: '--font-secondary',
  display: 'swap',
});

const decorative = Great_Vibes({
  weight: '400',
  subsets: ['latin'],
  variable: '--font-decorative',
  display: 'swap',
});

export default function App({ Component, pageProps }: AppProps) {
  useEffect(() => {
    // Handle service worker unregistration
    if (typeof window !== 'undefined' && 'serviceWorker' in navigator) {
      navigator.serviceWorker.getRegistrations().then(function(registrations) {
        for(const registration of registrations) {
          registration.unregister();
        }
      });
    }
  }, []);

  return (
    <>
      <Head>
        <title>{`${graduationConfig.graduateName} - Graduation Day`}</title>
        <meta name="description" content={`Join ${graduationConfig.graduateName} for a graduation ceremony at HUST on 26 September 2026.`} />
        <meta property="og:title" content={`${graduationConfig.graduateName} - Graduation Day`} />
        <meta property="og:description" content="A graduation ceremony invitation at Hanoi University of Science and Technology." />
        <meta property="og:image" content="/images/og-image.webp" />
        <link rel="icon" href="/favicon.ico" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
      </Head>
      <main className={`${primary.variable} ${secondary.variable} ${decorative.variable} font-sans`}>
        <Component {...pageProps} />
      </main>
    </>
  );
}
