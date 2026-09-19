/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'export',
  images: {
    unoptimized: true,
  },
  basePath: process.env.NODE_ENV === 'production' ? '/graduation-invitation' : '',
  assetPrefix: process.env.NODE_ENV === 'production' ? '/graduation-invitation/' : '',
  experimental: {
    disableOptimizedLoading: true,
  }
}

module.exports = nextConfig 