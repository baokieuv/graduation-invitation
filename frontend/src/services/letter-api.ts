import { InvitationLetter } from '@/types/letter';

const defaultLetter: InvitationLetter = {
  recipient: 'Friend',
  greeting: 'Dear friend,',
  body: 'I am delighted to invite you to celebrate my graduation ceremony. Your support has been an important part of my journey, and it would mean a lot to have you with me as I begin this new chapter.',
  signature: 'With gratitude,\nKieu Bao'
};

export const getDefaultLetter = (): InvitationLetter => ({ ...defaultLetter });

export async function lookupMockLetter(identifier: string): Promise<InvitationLetter> {
  await new Promise((resolve) => setTimeout(resolve, 350));

  const recipient = identifier.trim() || defaultLetter.recipient;
  return {
    recipient,
    greeting: `Dear friend,`,
    body: 'I am delighted to invite you to celebrate my graduation ceremony. Your support has been an important part of my journey, and it would mean a lot to have you with me as I begin this new chapter.',
    signature: 'With gratitude,\nKieu Bao'
  };
}
