export interface User {
  userId: number;

  firstName: string;

  lastName: string;

  username: string;

  email: string;

  password: string;

  phoneNumber?: string;

  addressLine1?: string;

  addressLine2?: string;

  city?: string;

  state?: string;

  zipCode?: string;

  country?: string;
}
