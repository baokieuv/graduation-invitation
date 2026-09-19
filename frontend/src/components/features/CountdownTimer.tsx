import { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { graduationConfig } from '../../config/graduation-config';
import { activeTheme } from '../../config/theme-config';
import { publicPath } from '../../config/public-path';
import { scrollAnimation, viewportSettings } from '../animations/scrollAnimations';
import { useLanguage } from '@/contexts/LanguageContext';

interface TimeLeft {
  days: number;
  hours: number;
  minutes: number;
  seconds: number;
}

export default function CountdownTimer() {
  const { t } = useLanguage();
  const [timeLeft, setTimeLeft] = useState<TimeLeft>({
    days: 0,
    hours: 0,
    minutes: 0,
    seconds: 0
  });

  useEffect(() => {
    const calculateTimeLeft = () => {
      const difference = +new Date(graduationConfig.date) - +new Date();
      
      if (difference > 0) {
        setTimeLeft({
          days: Math.floor(difference / (1000 * 60 * 60 * 24)),
          hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
          minutes: Math.floor((difference / 1000 / 60) % 60),
          seconds: Math.floor((difference / 1000) % 60)
        });
      }
    };

    const timer = setInterval(calculateTimeLeft, 1000);
    calculateTimeLeft();

    return () => clearInterval(timer);
  }, []);

  const timeUnits = [
    { label: t.days, value: timeLeft.days },
    { label: t.hours, value: timeLeft.hours },
    { label: t.minutes, value: timeLeft.minutes },
    { label: t.seconds, value: timeLeft.seconds },
  ];

  if (!graduationConfig.countdownTimer) {
    return null;
  }

  return (
    <motion.div
      className="relative pt-8 pb-16"
      style={{ backgroundColor: activeTheme.background }}
      variants={scrollAnimation}
      initial="offscreen"
      whileInView="onscreen"
      viewport={viewportSettings}
    >
      <div className="absolute inset-0 opacity-10" style={{ 
        backgroundImage: `url("${publicPath('/images/pattern/subtle-pattern.jpg')}")`,
        backgroundRepeat: 'repeat' 
      }} />
      
      <div className="container mx-auto px-4">
        <motion.h2
          variants={scrollAnimation}
          initial="offscreen"
          whileInView="onscreen"
          viewport={viewportSettings}
          className="text-center text-2xl md:text-3xl font-serif mb-8"
          style={{ color: activeTheme.text }}
        >
          {t.countdown}
        </motion.h2>

        <div className="grid grid-cols-2 md:grid-cols-4 gap-4 max-w-4xl mx-auto">
          {timeUnits.map(({ label, value }, index) => (
            <motion.div
              key={label}
              variants={scrollAnimation}
              initial="offscreen"
              whileInView="onscreen"
              viewport={viewportSettings}
              transition={{ delay: index * 0.1 }}
              className="relative"
            >
              <div className="bg-white rounded-lg shadow-lg p-6 transform hover:scale-105 transition-transform duration-300"
                   style={{ borderColor: activeTheme.accent, borderWidth: '1px' }}>
                <div className="text-4xl md:text-5xl font-bold mb-2" 
                     style={{ color: activeTheme.primary }}>
                  {value.toString().padStart(2, '0')}
                </div>
                <div className="text-sm md:text-base capitalize" 
                     style={{ color: activeTheme.text }}>
                  {label}
                </div>
              </div>
            </motion.div>
          ))}
        </div>
      </div>
    </motion.div>
  );
}
