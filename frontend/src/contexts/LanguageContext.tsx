import { createContext, useContext, useEffect, useState } from 'react';
import { GlobeAltIcon } from '@heroicons/react/24/outline';

type Language = 'en' | 'vi';

type Translation = {
  invitationIntro: string;
  celebrates: string;
  newChapter: string;
  findLetter: string;
  identifierPlaceholder: string;
  lookup: string;
  lookingUp: string;
  letterForYou: string;
  graduationInvitation: string;
  defaultLetterHint: string;
  saveTheDate: string;
  eventIntro: string;
  graduationCeremony: string;
  viewLocation: string;
  addToCalendar: string;
  contactMe: string;
  countdown: string;
  days: string;
  hours: string;
  minutes: string;
  seconds: string;
  facebook: string;
  languageLabel: string;
  english: string;
  vietnamese: string;
  lookupFallback: string;
};

const translations: Record<Language, Translation> = {
  en: {
    invitationIntro: 'You are warmly invited to my graduation ceremony.',
    celebrates: 'celebrates',
    newChapter: 'a new chapter',
    findLetter: 'Find your personal letter',
    identifierPlaceholder: 'Name, phone number, or student ID',
    lookup: 'Show my letter',
    lookingUp: 'Looking up...',
    letterForYou: 'A letter for you',
    graduationInvitation: 'Your Graduation Invitation',
    defaultLetterHint: 'Enter your name, phone number, or student ID above to reveal your personal invitation.',
    saveTheDate: 'Save the Date',
    eventIntro: 'Join me as I celebrate this milestone',
    graduationCeremony: 'Graduation Ceremony',
    viewLocation: 'View Location',
    addToCalendar: 'Add to Google Calendar',
    contactMe: 'Contact Me',
    countdown: 'Counting Down to Graduation Day',
    days: 'days',
    hours: 'hours',
    minutes: 'minutes',
    seconds: 'seconds',
    facebook: 'Facebook',
    languageLabel: 'Choose language',
    english: 'English',
    vietnamese: 'Vietnamese',
    lookupFallback: 'We could not find a personal letter, so we are showing the general invitation.'
  },
  vi: {
    invitationIntro: 'Trân trọng kính mời bạn đến dự lễ tốt nghiệp của tôi.',
    celebrates: 'đón chào',
    newChapter: 'một chặng đường mới',
    findLetter: 'Tìm thư mời dành cho bạn',
    identifierPlaceholder: 'Tên, số điện thoại hoặc mã sinh viên',
    lookup: 'Xem thư mời',
    lookingUp: 'Đang tìm...',
    letterForYou: 'Thư mời dành cho bạn',
    graduationInvitation: 'Lời mời dự lễ tốt nghiệp',
    defaultLetterHint: 'Nhập tên, số điện thoại hoặc mã sinh viên ở trên để xem thư mời dành cho bạn.',
    saveTheDate: 'Lưu ngày đặc biệt',
    eventIntro: 'Hãy cùng tôi kỷ niệm cột mốc đáng nhớ này',
    graduationCeremony: 'Lễ tốt nghiệp',
    viewLocation: 'Xem địa điểm',
    addToCalendar: 'Thêm vào Google Calendar',
    contactMe: 'Thông tin liên hệ',
    countdown: 'Đếm ngược đến ngày tốt nghiệp',
    days: 'ngày',
    hours: 'giờ',
    minutes: 'phút',
    seconds: 'giây',
    facebook: 'Facebook',
    languageLabel: 'Chọn ngôn ngữ',
    english: 'Tiếng Anh',
    vietnamese: 'Tiếng Việt',
    lookupFallback: 'Không tìm thấy thư cá nhân, hiển thị thư mời chung.'
  }
};

type LanguageContextValue = {
  language: Language;
  setLanguage: (language: Language) => void;
  t: Translation;
};

const LanguageContext = createContext<LanguageContextValue | undefined>(undefined);

export function LanguageProvider({ children }: { children: React.ReactNode }) {
  const [language, setLanguage] = useState<Language>('en');

  useEffect(() => {
    const savedLanguage = window.localStorage.getItem('graduation-language');
    if (savedLanguage === 'en' || savedLanguage === 'vi') {
      setLanguage(savedLanguage);
    }
  }, []);

  const changeLanguage = (nextLanguage: Language) => {
    setLanguage(nextLanguage);
    window.localStorage.setItem('graduation-language', nextLanguage);
  };

  return (
    <LanguageContext.Provider value={{ language, setLanguage: changeLanguage, t: translations[language] }}>
      {children}
      <LanguageSwitcher language={language} setLanguage={changeLanguage} t={translations[language]} />
    </LanguageContext.Provider>
  );
}

function LanguageSwitcher({ language, setLanguage, t }: { language: Language; setLanguage: (language: Language) => void; t: Translation }) {
  const [open, setOpen] = useState(false);

  return (
    <>
    {false && (
    <div className="fixed bottom-5 right-5 z-[100] disabled">
      {open && (
        <div className="mb-3 w-44 rounded-xl border border-yellow-200 bg-white p-2 shadow-xl" role="menu" aria-label={t.languageLabel}>
          <button
            type="button"
            onClick={() => { setLanguage('en'); setOpen(false); }}
            className={`flex w-full items-center justify-between rounded-lg px-3 py-2 text-left text-sm ${language === 'en' ? 'bg-red-50 font-semibold text-red-700' : 'text-slate-700 hover:bg-slate-50'}`}
          >
            {t.english}<span>EN</span>
          </button>
          <button
            type="button"
            onClick={() => { setLanguage('vi'); setOpen(false); }}
            className={`flex w-full items-center justify-between rounded-lg px-3 py-2 text-left text-sm ${language === 'vi' ? 'bg-red-50 font-semibold text-red-700' : 'text-slate-700 hover:bg-slate-50'}`}
          >
            {t.vietnamese}<span>VI</span>
          </button>
        </div>
      )}
      <button
        type="button"
        onClick={() => setOpen((isOpen) => !isOpen)}
        aria-label={t.languageLabel}
        aria-expanded={open}
        className="flex h-12 w-12 items-center justify-center rounded-full border-2 border-yellow-400 bg-red-700 text-white shadow-lg transition hover:bg-red-800 focus:outline-none focus:ring-2 focus:ring-yellow-400 focus:ring-offset-2"
      >
        <GlobeAltIcon className="h-6 w-6" />
      </button>
    </div>
    )}
    </>
  );
}

export function useLanguage() {
  const context = useContext(LanguageContext);
  if (!context) throw new Error('useLanguage must be used within a LanguageProvider');
  return context;
}
