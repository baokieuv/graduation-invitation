import { motion } from 'framer-motion';
import { graduationConfig } from '../../config/graduation-config';
import { scrollAnimation, viewportSettings } from '../animations/scrollAnimations';
import { publicPath } from '@/config/public-path';
import { useLanguage } from '@/contexts/LanguageContext';

interface HeroProps {
  identifier: string;
  onIdentifierChange: (value: string) => void;
  onLookup: (value: string) => void;
  loading: boolean;
  error: string;
}

export default function Hero({ identifier, onIdentifierChange, onLookup, loading, error }: HeroProps) {
  const { t } = useLanguage();
  return (
    <motion.section 
      className="relative z-0 min-h-screen flex items-center justify-center overflow-hidden pb-20"
      variants={scrollAnimation}
      initial="offscreen"
      whileInView="onscreen"
      viewport={viewportSettings}
    >
      {/* Background Image with Animated Overlay */}
      <div
        className="pointer-events-none absolute inset-0 bg-cover bg-center bg-no-repeat"
        style={{
          backgroundImage: `url("${publicPath('/images/background/hero-bg.png')}")`,
          maskImage: `url(${publicPath('/images/background/hero-bg.png')})`,
          maskPosition: 'center',
          maskRepeat: 'repeat'
        }}
      >
        <motion.div
          className="absolute inset-0 bg-red-950/70"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 1.5 }}
        />
        
        {/* Floating Flower Patterns */}
        <motion.img
          src={publicPath('/images/pattern/pattern-2.png')}
          alt=""
          className="absolute top-[20%] left-[5%] w-24 opacity-80 z-20"
          initial={{ y: 0, rotate: 0 }}
          animate={{ y: [0, -40, 0], rotate: [0, 15, 0] }}
          transition={{
            duration: 6,
            repeat: Infinity,
            repeatType: 'mirror',
            ease: 'easeInOut'
          }}
        />
        <motion.img
          src={publicPath('/images/pattern/pattern-3.webp')}
          alt=""
          className="absolute top-[40%] right-[10%] w-20 opacity-80 z-20"
          initial={{ y: 0, rotate: 0 }}
          animate={{ y: [0, -30, 0], rotate: [0, -10, 0] }}
          transition={{
            duration: 7,
            repeat: Infinity,
            repeatType: 'mirror',
            ease: 'easeInOut',
            delay: 1
          }}
        />
        <motion.img
          src={publicPath('/images/pattern/pattern-4.webp')}
          alt=""
          className="absolute bottom-[15%] left-[15%] w-24 opacity-80 z-20"
          initial={{ y: 0, rotate: 0 }}
          animate={{ y: [0, -50, 0], rotate: [0, 20, 0] }}
          transition={{
            duration: 8,
            repeat: Infinity,
            repeatType: 'mirror',
            ease: 'easeInOut',
            delay: 2
          }}
        />
        <motion.img
          src={publicPath('/images/pattern/pattern-5.png')}
          alt=""
          className="absolute top-[5%] right-[20%] w-12 opacity-70 z-20"
          initial={{ y: 0, rotate: 0 }}
          animate={{ y: [0, -25, 0], rotate: [0, 10, 0] }}
          transition={{
            duration: 5,
            repeat: Infinity,
            repeatType: 'mirror',
            ease: 'easeInOut',
            delay: 0.5
          }}
        />
        <motion.img
          src={publicPath('/images/pattern/pattern-1.png')}
          alt=""
          className="absolute bottom-[5%] right-[5%] w-32 opacity-70 z-20"
          initial={{ y: 0, rotate: 0 }}
          animate={{ y: [0, -35, 0], rotate: [0, -15, 0] }}
          transition={{
            duration: 6,
            repeat: Infinity,
            repeatType: 'mirror',
            ease: 'easeInOut',
            delay: 1.5
          }}
        />
      </div>

      {/* Content */}
      <div className="relative z-30 text-center px-4 max-w-2xl mx-auto">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 1 }}
          className="relative overflow-hidden rounded-2xl border border-yellow-400/60 bg-red-950/75 p-6 shadow-2xl backdrop-blur-md"
        >
          {/* Creative Decorative Elements */}
          <div className="absolute -bottom-8 -right-8 w-20 opacity-70 animate-float-slow">
            <img
              src={publicPath('/images/pattern/pattern-5.png')}
              alt=""
              className="w-full h-full"
            />
          </div>
          
          {/* Subtle Glow Effect */}
          <div className="pointer-events-none absolute inset-0 rounded-2xl animate-glow" />
          
          {/* Floating Petals Background */}
          <div className="pointer-events-none absolute inset-0 overflow-hidden">
            <div className="absolute w-4 h-4 bg-white/10 rounded-full top-[10%] left-[5%] animate-petal-float-1" />
            <div className="absolute w-3 h-3 bg-white/10 rounded-full top-[20%] left-[20%] animate-petal-float-2" />
            <div className="absolute w-4 h-4 bg-white/10 rounded-full top-[15%] right-[10%] animate-petal-float-3" />
            <div className="absolute w-3 h-3 bg-white/10 rounded-full bottom-[10%] left-[15%] animate-petal-float-4" />
          </div>
          <motion.p
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.5 }}
            className="mb-4 text-xl text-yellow-100"
          >
              {t.invitationIntro}
          </motion.p>

          <motion.div className="space-y-4">
            <motion.h1
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.8, duration: 1 }}
              className="text-4xl font-serif text-white md:text-6xl"
            >
              {graduationConfig.graduateName}
            </motion.h1>
            
            <motion.div
              initial={{ scale: 0 }}
              animate={{ scale: 1 }}
              transition={{ delay: 1.2, type: 'spring', stiffness: 100 }}
              className="text-2xl text-yellow-300 md:text-3xl"
            >
              {t.celebrates}
            </motion.div>
            
            <motion.h1
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 1.4, duration: 1 }}
              className="text-4xl font-serif text-yellow-300 md:text-6xl"
            >
              {t.newChapter}
            </motion.h1>
          </motion.div>

          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 1.1 }}
            className="mt-4 text-lg text-white"
          >
            {new Date(graduationConfig.date).toLocaleDateString('en-GB', {
              day: 'numeric',
              month: 'long',
              year: 'numeric'
            })}
          </motion.div>

          <form onSubmit={(event) => { event.preventDefault(); onLookup(identifier); }} className="relative z-40 mt-6 text-left">
            <label htmlFor="guest-identifier" className="mb-2 block text-sm text-yellow-100">
              {t.findLetter}
            </label>
            <div className="flex flex-col sm:flex-row gap-3">
              <input
                id="guest-identifier"
                value={identifier}
                onChange={(event) => onIdentifierChange(event.target.value)}
                placeholder={t.identifierPlaceholder}
                className="relative z-50 min-w-0 flex-1 rounded-lg border border-yellow-300 bg-white px-4 py-3 text-slate-900 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-yellow-400"
              />
              <button type="submit" disabled={loading} className="rounded-lg bg-yellow-500 px-5 py-3 font-semibold text-red-950 transition hover:bg-yellow-400 disabled:cursor-wait disabled:opacity-60">
                {loading ? t.lookingUp : t.lookup}
              </button>
            </div>
            {error && <p className="mt-2 text-sm text-red-200" role="alert">{error}</p>}
          </form>
        </motion.div>
      </div>

      {/* Scroll Indicator */}
      <motion.div 
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 1.5 }}
        className="absolute bottom-8 left-1/2 transform -translate-x-1/2 text-white"
      >
        <div className="animate-bounce">
          <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 14l-7 7m0 0l-7-7m7 7V3" />
          </svg>
        </div>
      </motion.div>
    </motion.section>
  );
}
