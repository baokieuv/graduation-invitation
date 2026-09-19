import { motion } from 'framer-motion';
import { scrollAnimation, viewportSettings } from '../animations/scrollAnimations';
import { InvitationLetter } from '@/types/letter';
import { useLanguage } from '@/contexts/LanguageContext';

interface GuestBookProps {
  letter: InvitationLetter | null;
}

export default function GuestBook({ letter }: GuestBookProps) {
  const { t } = useLanguage();
  return (
    <section id="invitation-letter" className="scroll-mt-6 bg-white py-20" aria-live="polite">
      <div className="container mx-auto px-4">
        <motion.div
          variants={scrollAnimation}
          initial="offscreen"
          whileInView="onscreen"
          viewport={viewportSettings}
          className="mx-auto max-w-2xl text-center"
        >
          <p className="mb-3 text-sm font-semibold uppercase tracking-[0.2em] text-red-700">{t.letterForYou}</p>
          <h2 className="mb-4 font-serif text-4xl text-red-700">{t.graduationInvitation}</h2>
          {!letter ? (
            <p className="text-slate-600">{t.defaultLetterHint}</p>
          ) : (
            <article className="mt-10 rounded-2xl border border-yellow-300 bg-red-50 p-8 text-left shadow-sm md:p-12">
              <p className="mb-6 font-serif text-2xl text-red-800">{letter.greeting || `Dear ${letter.recipient},`}</p>
              <p className="whitespace-pre-line leading-8 text-slate-700">{letter.body}</p>
              {letter.signature && <p className="mt-8 whitespace-pre-line font-serif text-xl text-slate-900">{letter.signature}</p>}
            </article>
          )}
        </motion.div>
      </div>
    </section>
  );
}
