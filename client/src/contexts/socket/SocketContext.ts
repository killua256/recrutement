import React from 'react';
import { Socket } from 'socket.io-client';
import { Config } from '@config/Config';
import { User } from '@shared/types';


export interface SocketContextInterface {
    socket: Socket | null;
    connect: (user: User) => void;
    disconnect: () => void;
}

export const SocketContextDefaults: SocketContextInterface = {
    socket: null,
    connect: (user: User) => null,
    disconnect: () => null
};


export const SocketContext = React.createContext<SocketContextInterface>(
    SocketContextDefaults
);