import { motion } from 'framer-motion';
import { graduationConfig } from '@/config/graduation-config';
import { activeTheme } from '@/config/theme-config';
import { useLanguage } from '@/contexts/LanguageContext';

export default function Footer() {
  const { language } = useLanguage();
  const copy = language === 'vi'
    ? {
        thanks: 'Cảm ơn bạn đã đồng hành và động viên tôi trong suốt chặng đường vừa qua.',
        celebrate: 'Tôi rất mong được cùng bạn kỷ niệm ngày tốt nghiệp tại HUST.',
        gratitude: 'Trân trọng, '
      }
    : {
        thanks: 'Thank you for being part of this milestone and for the encouragement that brought me here.',
        celebrate: 'I look forward to celebrating graduation day with you at HUST.',
        gratitude: 'With gratitude, '
      };
  return (
    <footer className="py-12 text-center" style={{ backgroundColor: activeTheme.primary }}>
      <div className="container mx-auto px-4">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          className="max-w-2xl mx-auto"
        >
          <h2 className="text-3xl font-serif text-white mb-4">
            {graduationConfig.graduateName}
          </h2>
          <p className="text-white/80 mb-8 max-w-2xl mx-auto">
            {copy.thanks}
          </p>
          <p className="text-white/80 mb-8 max-w-2xl mx-auto">
            {copy.celebrate}
          </p>
          <p className="text-white/80 mb-8 max-w-2xl mx-auto">
            {copy.gratitude}{graduationConfig.graduateName}
          </p>
          <div className="text-white/60 text-sm">
            <p>Graduation Day · {graduationConfig.university}</p>
            <p className="mt-2">© {new Date().getFullYear()} All rights reserved</p>
          </div>
        </motion.div>
      </div>
    </footer>
  );
}
