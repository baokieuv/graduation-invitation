import { motion } from 'framer-motion';
import Image from 'next/image';
import { graduationConfig } from '@/config/graduation-config';
import { CalendarIcon, MapPinIcon, ClockIcon, PhoneIcon, EnvelopeIcon, UserIcon } from '@heroicons/react/24/outline';
import { publicPath } from '@/config/public-path';
import { useLanguage } from '@/contexts/LanguageContext';

export default function EventDetails() {
  const { t } = useLanguage();
  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('id-ID', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  const events = [
    {
      title: t.graduationCeremony,
      details: {
        date: graduationConfig.date,
        time: '07:00',
        venue: graduationConfig.location,
        address: graduationConfig.address,
        mapLink: graduationConfig.mapLink
      }
    }
  ];

  const event = events[0];
  const eventDate = event.details.date.slice(0, 10).replace(/-/g, '');
  const eventTime = event.details.time.replace(':', '') + '00';
  const endTime = `${String(Number(event.details.time.slice(0, 2)) + 1).padStart(2, '0')}${event.details.time.slice(3)}00`;
  const googleCalendarUrl = `https://calendar.google.com/calendar/render?action=TEMPLATE&text=${encodeURIComponent(`${event.title} - ${graduationConfig.graduateName}`)}&dates=${eventDate}T${eventTime}/${eventDate}T${endTime}&details=${encodeURIComponent(`Graduation ceremony for ${graduationConfig.graduateName}.`)}&location=${encodeURIComponent(`${event.details.venue}, ${event.details.address}`)}&ctz=Asia/Ho_Chi_Minh`;

  return (
    <section className="py-20">
      <div className="container mx-auto px-4">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          className="text-center mb-16"
        >
          <div className="relative bg-black/20 backdrop-blur-sm rounded-lg p-6 max-w-2xl mx-auto">
            <h2 className="mb-4 font-serif text-4xl text-white">{t.saveTheDate}</h2>
            <p className="text-yellow-100">{t.eventIntro}</p>
          </div>
        </motion.div>

        {/* Cấu trúc Grid 2 cột chứa cả Sự kiện và Liên hệ */}
        <div className="grid md:grid-cols-2 gap-8 max-w-5xl mx-auto">
          {events.map((event, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ delay: index * 0.2 }}
              className="relative flex flex-col justify-between overflow-hidden rounded-lg border-t-4 border-red-700 bg-white p-8 shadow-lg"
              style={{ minHeight: '400px' }}
            >
              {event.title === t.graduationCeremony && (
                <>
                  <div className="absolute -top-8 -left-8 w-32 h-32 opacity-50">
                    <Image
                      src={publicPath('/images/pattern/pattern-5.png')}
                      alt=""
                      fill
                      className="object-contain"
                    />
                  </div>
                  <div className="absolute -bottom-8 -right-8 w-32 h-32 opacity-50">
                    <Image
                      src={publicPath('/images/pattern/pattern-1.png')}
                      alt=""
                      fill
                      className="object-contain"
                    />
                  </div>
                </>
              )}

              <div>
                <h3 className="mb-6 text-center font-serif text-2xl text-red-700">{event.title}</h3>
                
                <div className="space-y-6 relative z-10">
                  <div className="flex items-start gap-4">
                    <CalendarIcon className="h-6 w-6 shrink-0 text-red-700" />
                    <div>
                      <p className="font-medium">{formatDate(event.details.date)}</p>
                    </div>
                  </div>

                  <div className="flex items-start gap-4">
                    <ClockIcon className="h-6 w-6 shrink-0 text-red-700" />
                    <div>
                      <p className="font-medium">{event.details.time} WIB</p>
                    </div>
                  </div>

                  <div className="flex items-start gap-4">
                    <MapPinIcon className="h-6 w-6 shrink-0 text-red-700" />
                    <div>
                      <p className="font-medium">{event.details.venue}</p>
                      <p className="text-slate-600">{event.details.address}</p>
                    </div>
                  </div>
                </div>
              </div>

              <div className="relative z-10 mt-8">
                <a
                  href={event.details.mapLink}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="block w-full rounded-lg bg-red-50 py-3 text-center font-medium text-red-800 transition-colors hover:bg-red-100"
                >
                  {t.viewLocation}
                </a>
                <a
                  href={googleCalendarUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="mt-4 block w-full rounded-lg border-2 border-yellow-500 bg-yellow-500 px-6 py-4 text-center text-lg font-semibold text-red-950 shadow-lg transition-all duration-200 hover:bg-yellow-400 focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:ring-offset-2 active:bg-yellow-400"
                >
                  {t.addToCalendar}
                </a>
              </div>
            </motion.div>
          ))}

          {/* Phần Contact Me nằm ở cột thứ 2 */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ delay: 0.4 }}
            className="flex flex-col justify-center rounded-lg border-t-4 border-yellow-500 bg-white p-8 shadow-lg"
            style={{ minHeight: '400px' }}
          >
            <h3 className="mb-8 text-center font-serif text-2xl text-red-700">{t.contactMe}</h3>
            <div className="flex flex-col gap-6 px-4 text-slate-700">
              <div className="flex items-center gap-4">
                <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-red-50">
                  <UserIcon className="h-5 w-5 text-red-700" />
                </div>
                <span className="font-medium">{graduationConfig.contact.name}</span>
              </div>
              
              <a className="group flex items-center gap-4 transition-colors hover:text-red-700" href={`tel:${graduationConfig.contact.phone}`}>
                <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-red-50 transition-colors group-hover:bg-red-100">
                  <PhoneIcon className="h-5 w-5 text-red-700" />
                </div>
                <span className="font-medium">{graduationConfig.contact.phone}</span>
              </a>
              
              <a className="group flex items-center gap-4 break-all transition-colors hover:text-red-700" href={`mailto:${graduationConfig.contact.email}`}>
                <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-red-50 transition-colors group-hover:bg-red-100">
                  <EnvelopeIcon className="h-5 w-5 text-red-700" />
                </div>
                <span className="font-medium">{graduationConfig.contact.email}</span>
              </a>
              
              <a className="group flex items-center gap-4 transition-colors hover:text-red-700" href={graduationConfig.contact.facebook} target="_blank" rel="noopener noreferrer">
                <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-red-50 transition-colors group-hover:bg-red-100">
                  <span className="flex h-5 w-5 items-center justify-center rounded bg-red-700 text-xs font-bold text-white">f</span>
                </div>
                <span className="font-medium">{t.facebook}</span>
              </a>
            </div>
          </motion.div>
        </div>
      </div>
    </section>
  );
}