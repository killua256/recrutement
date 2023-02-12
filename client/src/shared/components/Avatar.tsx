import React from 'react'
import userDefault from '@assets/user_default.png'
import placeholder from '@assets/placeholder.png'

interface AvatarProps {
    user?: boolean
    src: string
    alt?: string
    className?: string
}

const Avatar = (props: AvatarProps) => {

    const onError = (event: any) => {
        event.target.setAttribute("src", props.user ? userDefault : placeholder)
        event.onerror = null;
    }

    return (
        <img src={props.src} alt={props.alt} onError={onError} 
        className={`rounded-full ${props.className}`} />
    )
}

export default Avatar