import { User } from '@shared/types';
import React from 'react';

export interface AuthContextInterface {
  user: User | null;
  login: (userData: User) => void;
  logout: () => void;
}

export const authContextDefaults: AuthContextInterface = {
  user: null,
  login: (userData: User) => null,
  logout: () => null
};

export const AuthContext = React.createContext<AuthContextInterface>(
  authContextDefaults
);