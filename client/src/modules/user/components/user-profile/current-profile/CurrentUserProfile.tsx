import { User } from '@shared/types'
import React from 'react'

interface CurrentUserProfileProps {
    userProfile: User | null
}

const CurrentUserProfile = ({
    userProfile
}: CurrentUserProfileProps) => {

    return (
        <div> {userProfile?.displayName} </div>
    )
}

export default CurrentUserProfile