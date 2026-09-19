import Document, { Html, Head, Main, NextScript } from 'next/document';
import { graduationConfig } from '@/config/graduation-config';
import { publicPath } from '@/config/public-path';

class MyDocument extends Document {
  render() {
    return (
      <Html lang="en">
        <Head>
          <meta charSet="utf-8" />
          <meta name="theme-color" content="#ffffff" />
          <link rel="icon" href={publicPath('/favicon.ico')} />
          <meta
            name="description"
            content={`The graduation ceremony invitation of ${graduationConfig.graduateName}`}
          />
          <meta property="og:type" content="website" />
          <meta property="og:locale" content="id_ID" />
          <meta property="og:site_name" content="Graduation Day Invitation" />
        </Head>
        <body>
          <Main />
          <NextScript />
        </body>
      </Html>
    );
  }
}

export default MyDocument;