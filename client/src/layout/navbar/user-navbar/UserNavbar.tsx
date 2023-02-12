import { Config } from '@config/Config';
import { AuthContext } from '@contexts/index';
import { Avatar, Button, Dropdown, Language } from '@shared/components';
import { DropdownItem, User } from '@shared/types';
import { getFile } from '@utils/fileHandler';
import React, { useContext } from 'react'
import { useTranslation } from 'react-i18next';
import { FaSignOutAlt } from 'react-icons/fa';
import { useNavigate, NavLink } from 'react-router-dom';
import NavItem from '../nav-item/NavItem';

const UserNavbar = () => {
    const { user, logout } = useContext(AuthContext)
    const navigate = useNavigate()
    const { t } = useTranslation();


    const onLogout = () => {
        logout()
        navigate('/')
    }

    const dropdownItems = (user: User): DropdownItem[] => {
        return [
            {
                component: (
                    <div className="flex items-center gap-4">
                        <Avatar user className="h-8 w-8 rounded-full"
                            src={getFile(user?.avatar!)}
                            alt={user?.displayName} />
                        {user?.displayName}
                    </div>
                ),
                isLink: true,
                action: `/in/${user?.username}`
            },
            {
                component: (
                    <div className="flex items-center gap-4">
                        <FaSignOutAlt />
                        Sign out
                    </div>
                ),
                action: onLogout
            }
        ]
    }
    return (
        <>
            <ul className='hidden xs:flex items-center justify-center gap-4 sm:gap-0 h-full'>
                {
                    Config.getMenu().map((menu: any) => (
                        <NavItem key={menu.label} menu={menu} />
                    ))
                }
            </ul>
            <div className='flex items-center gap-4'>
                <Language />
                <Dropdown trigger={(
                    <Avatar user className="h-10 w-10 rounded-full"
                        src={getFile(user?.avatar!)}
                        alt={user?.displayName} />
                )}
                    items={dropdownItems(user!)}
                />
            </div>
        </>
    )
}

export default UserNavbar