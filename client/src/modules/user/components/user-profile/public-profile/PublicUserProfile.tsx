import { User } from '@shared/types'
import React from 'react'

interface PublicUserProfileProps {
    userProfile: User | null
}

const PublicUserProfile = ({
    userProfile
}: PublicUserProfileProps) => {
  return (
    <div> {userProfile?.displayName} </div>
  )
}

export default PublicUserProfile