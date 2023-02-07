import React from 'react'
import { Link } from 'react-router-dom'

interface AppLinkProps {
    to: string
    color?: string
    border?: boolean
    children?: any
}

const AppLink = ({
    to,
    color = 'primary',
    border,
    children
}: AppLinkProps) => {
    const borderClass = border ? `border border-${color}-500` : 'border-none'
    const className = `text-${color}-500 rounded ${borderClass} hover:bg-${color}-50 font-bold uppercase text-xs px-4 py-2 hover:shadow-ml outline-none focus:outline-none ease-linear transition-all duration-150 disabled:text-gray-400 disabled:cursor-not-allowed`
    return (
        <Link to={to} className={className} >
            {children}
        </Link>
    )
}

export default AppLink