import { InvitationLetter } from '@/types/letter';

const INVITATION_API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1/invitation';

// const INVITATION_API_URL = 'http://localhost:8080/api/v1/invitation';

const defaultLetter: InvitationLetter = {
  recipient: 'Friend',
  greeting: 'Dear friend,',
  body: 'I am delighted to invite you to celebrate my graduation ceremony. Your support has been an important part of my journey, and it would mean a lot to have you with me as I begin this new chapter.',
  signature: 'With gratitude,\nKieu Bao'
};

export const getDefaultLetter = (): InvitationLetter => ({ ...defaultLetter });

interface InvitationResponse {
  success: boolean;
  code: string;
  message: string;
  data: {
    title: string;
    content: string;
  };
}

export async function lookupLetter(identifier: string): Promise<InvitationLetter> {
  const response = await fetch(`${INVITATION_API_URL}?identifier=${encodeURIComponent(identifier)}`);
  const payload = await response.json().catch(() => null) as InvitationResponse | { message?: string } | null;

  if (!response.ok) {
    const message = payload && 'message' in payload ? payload.message : undefined;
    throw new Error(message || 'We could not find your letter.');
  }

  if (
    !payload ||
    !('data' in payload) ||
    !payload.data ||
    !payload.data.title ||
    !payload.data.content
  ) {
    throw new Error('The letter response is missing its title or content.');
  }

  return {
    recipient: identifier.trim(),
    greeting: payload.data.title,
    body: payload.data.content,
    signature: 'Trân trọng,\nKieu Bao'
  };
}
