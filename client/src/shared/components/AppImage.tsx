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

    const onError = (event: any) => {
        if(props.type == "COVER"){
            event.target.setAttribute("src", cover)
        } else {
            event.target.setAttribute("src", placeholder)
        }
        event.onerror = null;
    }

    return (
        <img src={props.src} alt={props.alt} onError={onError} className={props.className} />
    )
}

export default AppImage