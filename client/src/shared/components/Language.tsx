import { DropdownItem } from '@shared/types';
import React from 'react'
import { useTranslation } from 'react-i18next';
import { FaChevronDown } from 'react-icons/fa';
import Dropdown from './Dropdown';

const Language = () => {
    const { i18n } = useTranslation();

    const formatLang = (lang: string): string => {
        let res: string = '';
        if (lang != undefined && lang != '') {
            if (lang.includes('-')) {
                res = lang.split('-')[0];
            } else {
                res = lang;
            }
        }
        return res.toUpperCase();
    }

    const selectedLang = (lang: string) => {
        return lang.toUpperCase() === formatLang(i18n.language)
    }

    const displayComponent = (label: string) => (
        <span className="block w-full p-1"> {label} </span>
    )


    const langs : DropdownItem[]= [
        {
            component: displayComponent('English'),
            active: selectedLang('en'),
            action: () => i18n.changeLanguage('en')
        },
        {
            component: displayComponent('Français'),
            active: selectedLang('fr'),
            action: () => i18n.changeLanguage('fr')
        }
    ]

    const trigger = (
        <>
            <span className='text-sm text-gray-600 font-medium'>
                {formatLang(i18n.language)}
                </span>
            {/* <FaChevronDown
                className="w-4 h-4 ml-1 -mr-1 text-gray-600"
                aria-hidden="true"/> */}
        </>
    )

    return (
        <Dropdown trigger={trigger} items={langs} size="w-22" />
    )
}

export default Language;
