import React from 'react'
import { useTranslation } from 'react-i18next';
import { NavLink } from 'react-router-dom'

const NavItem = ({ menu }: any) => {
    const { t } = useTranslation();

    const getImageUrl = (name: string) => {
        return new URL(`../../../assets/svg/${name}.svg`, import.meta.url).href
    }
    return (
        <NavLink title={t(`titles.${menu.label}`)!} className="h-full"
            key={menu.label} to={menu.url}>
            <li className="px-2 h-full cursor-pointer rounded-sm flex flex-col items-center justify-center hover:bg-gray-200">
                <img src={getImageUrl(menu.icon)} className="svg-secondary" />
                <span className="hidden sm:block text-xs mx-2"> {t(`titles.${menu.label}`)} </span>
            </li>
        </NavLink>
    )
}

export default NavItem