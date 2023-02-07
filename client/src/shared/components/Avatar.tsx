import React, { useEffect, useState } from 'react'
import userDefault from '@assets/user_default.png'
import placeholder from '@assets/placeholder.png'

interface AvatarProps {
    user?: boolean
    [x: string]: any;
}

const Avatar = (props: AvatarProps) => {
    const [src, setSrc] = useState(props.user ? userDefault : placeholder)

    const onError = (event: any) => {
        event.target.setAttribute("src", props.user ? userDefault : placeholder)
        event.onerror = null;
    }

    useEffect(() => {
        const img = new Image();
        img.src = props.src;
        img.onload = () => {
            setSrc(src);
        };
    }, [src]);

    return (
        <img src={src} alt={props.alt} onError={onError} {...props} />
    )
}

export default Avatar