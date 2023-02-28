import React, { useState } from 'react'
import userDefault from '@assets/user_default.png'
import placeholder from '@assets/placeholder.png'

interface AvatarProps {
    user?: boolean
    src: string
    alt?: string
    className?: string
}

const Avatar = (props: AvatarProps) => {

    const [loading, setLoading] = useState<boolean>(true)
    const [error, setError] = useState<boolean>(false)

    const onError = (event: any) => {
        setLoading(false)
        setError(true)
    }

    const onLoad = (event: any) => {
        setLoading(false)
        setError(false)
    }

    const getPlaceholder = () => {
        return props.user ? userDefault : placeholder
    }

    return (
        <>
            <img src={getPlaceholder()} alt={props.alt}
                className={`rounded-full ${props.className} ${loading ? 'animate-pulse' : ''}`}
                style={{
                    display: loading || error ? 'block' : 'none'
                }} />
            <img src={props.src} alt={props.alt} onLoad={onLoad}
                onError={onError} className={`rounded-full ${props.className}`}
                style={{
                    display: loading || error ? 'none' : 'block'
                }} />
        </>
    )
}

export default Avatar