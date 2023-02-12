import { AuthContext } from '@contexts/index'
import userService from '@modules/settings/services/user.service'
import { AppImage, Avatar, PageLoading } from '@shared/components'
import { User } from '@shared/types'
import { getFile } from '@utils/fileHandler'
import React, { useContext, useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import CurrentUserProfile from './current-profile/CurrentUserProfile'
import PublicUserProfile from './public-profile/PublicUserProfile'

const UserProfile = () => {
    const { user } = useContext(AuthContext)
    const { username } = useParams()
    const navigate = useNavigate()

    const [userProfile, setUserProfile] = useState<User | null>(null)
    const [loading, setLoading] = useState<Boolean>(false)

    const loadUserProfile = async () => {
        setLoading(true)
        if (!username || username.trim() == "") {
            navigate('/')
            return;
        }

        const { response, success } = await userService.findByUsername(username);
        if (success && response) {
            setUserProfile(response)
            setLoading(false)
        } else {
            navigate('/')
        }
    }

    useEffect(() => {
        loadUserProfile()
    }, [])

    return loading ? <PageLoading /> :
        <div className='w-11/12 max-w-screen-md mx-auto my-4 bg-white rounded-md shadow-md'>

            <AppImage src={getFile('users', userProfile?.coverPath!)}
                type="COVER" alt="cover" className="w-full max-h-48 rounded-t-md" />
            <div className='p-8'>
                <Avatar user className="h-20 w-20 -mt-16 md:h-32 md:w-32 md:-mt-24 z-10 relative border-4 border-white border-solid rounded-full"
                    src={getFile('users', userProfile?.imagePath!)}
                    alt={userProfile?.displayName} />
                <h4 className='text-xl md:text-2xl font-semibold'>
                    {userProfile?.displayName}
                </h4>
            </div>
        </div>
    // user?.username == username ?
    //     <CurrentUserProfile userProfile={userProfile} /> :
    //     <PublicUserProfile userProfile={userProfile} />
}

export default UserProfile