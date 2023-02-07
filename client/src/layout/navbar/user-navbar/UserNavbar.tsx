import { Config } from '@config/Config';
import { AuthContext } from '@contexts/index';
import { Avatar, Button, Dropdown } from '@shared/components';
import { DropdownItem, User } from '@shared/types';
import { getFile } from '@utils/fileHandler';
import React, { useContext } from 'react'
import { useTranslation } from 'react-i18next';
import { useNavigate, NavLink } from 'react-router-dom';

const UserNavbar = () => {
    const { user, logout } = useContext(AuthContext)
    const navigate = useNavigate()
    const { t } = useTranslation();


    const onLogout = () => {
        logout()
        navigate('/')
    }

    const toProfil = () => {
        navigate('/profile')
    }

    const dropdownItems = (user: User): DropdownItem[] => {
        return [
            {
                component: (
                    <div className="flex items-center gap-4">
                        <Avatar user className="h-8 w-8 rounded-full"
                            src={getFile('users', user?.imagePath!)}
                            alt={user?.displayName} />
                        {user?.displayName}
                    </div>
                ),
                action: toProfil
            },
            {
                label: 'Sign out',
                action: onLogout
            }
        ]
    }
    return (
        <>
            <ul className='hidden md:flex items-center justify-center h-full'>
                {
                    Config.getMenu().map((menu: any) => (
                        <NavLink className="h-full"
                            key={menu.label} to={menu.url}>
                            <li className="px-2 h-full cursor-pointer rounded-sm flex items-center justify-between hover:bg-gray-200">
                                <span className="mx-4"> {t(`titles.${menu.label}`)} </span>
                            </li>
                        </NavLink>
                    ))
                }
            </ul>
            <div className='flex items-center gap-4'>
                {
                    user ? (
                        <Dropdown trigger={(
                            <Avatar user className="h-10 w-h-10 rounded-full"
                                src={getFile('users', user?.imagePath!)}
                                alt={user?.displayName} />
                        )}
                            items={dropdownItems(user!)}
                        />
                    ) : (
                        <Button color='primary' onClick={() => navigate('/login')}>
                            Login
                        </Button>
                    )
                }
            </div>
        </>
    )
}

export default UserNavbar