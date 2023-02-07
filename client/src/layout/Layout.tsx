import React, { useContext, useEffect, useState } from 'react'
import Navbar from './navbar/Navbar'
import { AuthContext, SettingsContext, SocketContext } from '@contexts/index'

const Layout = ({ children }: any) => {
    const { user } = useContext(AuthContext)
    const { socket, connect, disconnect } = useContext(SocketContext)
    const { settings, setSettings } = useContext(SettingsContext)

    useEffect(() => {
        

        if(user){
            disconnect()
            connect(user)
        }

        return () => {
            disconnect()
        }
    }, [])
    return (
        <>
            <Navbar />

            <main className='bg-slate-50 fixed w-full h-full overflow-auto'>
                {children}
            </main>
        </>
    )
}

export default Layout
