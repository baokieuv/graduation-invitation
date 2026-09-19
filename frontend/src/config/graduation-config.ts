export interface GraduationConfig {
  graduateName: string;
  degree: string;
  university: string;
  date: string;
  location: string;
  address: string;
  mapLink: string;
  contact: {
    name: string;
    phone: string;
    email: string;
    facebook: string;
  };
  countdownTimer: boolean;
}

export const graduationConfig: GraduationConfig = {
  graduateName: "Kieu Bao",
  degree: "Bachelor of Engineering",
  university: "Hanoi University of Science and Technology",
  date: "2026-09-26T07:00:00+07:00",
  location: "HUST, Hanoi",
  address: "1 Dai Co Viet, Hai Ba Trung, Hanoi",
  mapLink: "https://maps.app.goo.gl/tN6XR7jG6HsGD6j6A",
  contact: {
    name: "Kieu Bao",
    phone: "0981722618",
    email: "kieubao2k4@gmail.com",
    facebook: "https://www.facebook.com/kieu.bao.839596"
  },
  countdownTimer: true
};
