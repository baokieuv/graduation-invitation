const productionBasePath = process.env.NODE_ENV === 'production' ? '/graduation-invitation' : '';

export function publicPath(path: string): string {
  return `${productionBasePath}${path.startsWith('/') ? path : `/${path}`}`;
}
