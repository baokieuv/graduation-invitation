const productionBasePath = '';

export function publicPath(path: string): string {
  return `${productionBasePath}${path.startsWith('/') ? path : `/${path}`}`;
}
