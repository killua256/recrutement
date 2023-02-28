import React, { useEffect, useState } from 'react'
import placeholder from '@assets/placeholder.png'
import cover from '@assets/cover.png'

interface AppImageProps {
    src: string
    alt?: string
    className?: string
    type?: string
}

const AppImage = (props: AppImageProps) => {

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
        if(props.type == "COVER"){
            return cover
        } else {
            return placeholder
        }
    }

    return (
        <>
            <img src={getPlaceholder()} alt={props.alt}
                className={`${props.className} ${loading ? 'animate-pulse' : ''}`} style={{
                    display: loading || error ? 'block' : 'none'
                }} />
            <img src={props.src} alt={props.alt} onLoad={onLoad}
                onError={onError} className={props.className} style={{
                    display: loading || error ? 'none' : 'block'
                }} />
        </>
    )
}

export default AppImage