import React from 'react';
import { graduationConfig } from '../../config/graduation-config';
import Footer from './Footer';
import Head from 'next/head';
import { ErrorBoundary } from 'react-error-boundary';
import { publicPath } from '../../config/public-path';
import { useLanguage } from '@/contexts/LanguageContext';

interface MainLayoutProps {
  children: React.ReactNode;
}

function ErrorFallback({ error }: { error: Error }) {
  return (
    <div role="alert" className="p-8 text-center">
      <h2 className="text-2xl font-bold text-graduation-text mb-4">Something went wrong</h2>
      <p className="text-graduation-text/80">{error.message}</p>
    </div>
  );
}

export default function MainLayout({ children }: MainLayoutProps) {
  const { language } = useLanguage();

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback}>
      <Head>
        <title>{`${graduationConfig.graduateName} - Graduation Day`}</title>
        <meta name="description" content={language === 'vi' ? `Thư mời lễ tốt nghiệp của ${graduationConfig.graduateName} tại HUST.` : `Graduation ceremony invitation for ${graduationConfig.graduateName} at HUST.`} />
      </Head>
      
      <div className="relative overflow-x-hidden">

        {/* Animated Background */}
        <div
          className="fixed inset-0 bg-repeat bg-[length:100px] opacity-50 animate-move-bg z-10 md:bg-[length:200px]"
          style={{ backgroundImage: `url("${publicPath('/images/pattern/subtle-pattern.jpg')}")` }}
        ></div>

        {/* Main Content */}
        <div className="relative z-30">
          {React.Children.map(children, (child) => (
            <section className="relative">
              {child}
            </section>
          ))}
          <Footer />
        </div>
      </div>
    </ErrorBoundary>
  );
}
