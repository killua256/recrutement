import React from 'react'
import { useTranslation } from 'react-i18next';
import { NavLink } from 'react-router-dom'

interface NavItemProps{
    menu: any
    inline?: boolean
}

const NavItem = ({
    menu,
    inline
}: NavItemProps) => {
    const { t } = useTranslation();

    const getImageUrl = (name: string) => {
        return new URL(`../../../assets/svg/${name}.svg`, import.meta.url).href
    }
    return (
        <NavLink title={t(`titles.${menu.label}`)!} className={inline ? '' : 'h-full'}
            key={menu.label} to={menu.url}>
            <li className={`cursor-pointer rounded-sm flex ${inline ? 'p-4' : 'flex-col justify-center h-full px-2 w-14 sm:w-24'} items-center hover:bg-gray-200`}>
                <img src={getImageUrl(menu.icon)} className="svg-secondary" />
                <span className={`${inline ? '' : 'hidden sm:block text-xs'} mx-2`}>
                    {t(`titles.${menu.label}`)}
                </span>
            </li>
        </NavLink>
    )
}

export default NavItem