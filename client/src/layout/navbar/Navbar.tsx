import React, { useContext } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { AuthContext } from '@contexts/auth/AuthContext'
import { useTranslation } from 'react-i18next'
import UserNavbar from './user-navbar/UserNavbar'
import GuestNavbar from './guest-navbar/GuestNavbar'
import { FaBars } from 'react-icons/fa'


const Navbar = ({
    toggleSidebar
}: any) => {
    const { user } = useContext(AuthContext)
    const navigate = useNavigate()
    const { t } = useTranslation();


    return (
        <header className="bg-slate-50 shadow-md h-16 flex items-center justify-center w-full border-b-2 border-b-gray-200">
            <div className="flex flex-grow items-center justify-between px-4 h-full">
                <div className='flex items-center justify-center'>
                    <button className="w-10 p-1 xs:hidden"
                        onClick={toggleSidebar}
                        title="Toggle menu"
                        tabIndex={1}
                    >
                        <FaBars className="text-black" size="25px" />
                    </button>
                    <Link to="/">
                        BRAND
                    </Link>
                </div>
                {
                    user ? <UserNavbar /> : <GuestNavbar />
                }

            </div>
        </header>
    )
}

export default Navbar