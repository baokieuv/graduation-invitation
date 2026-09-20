/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'standalone',
  // output: 'export',
  images: {
    unoptimized: true,
  },
  experimental: {
    disableOptimizedLoading: true,
  }
}

module.exports = nextConfig;