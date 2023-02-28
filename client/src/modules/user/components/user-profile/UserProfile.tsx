import { AuthContext } from '@contexts/index'
import userService from '@modules/user/services/user.service'
import { AppImage, Avatar, PageLoading } from '@shared/components'
import { User } from '@shared/types'
import { getFile } from '@utils/fileHandler'
import { toastError, toastSuccess } from '@utils/toast'
import React, { useContext, useEffect, useRef, useState } from 'react'
import { FaCamera } from 'react-icons/fa'
import { useNavigate, useParams } from 'react-router-dom'
import CurrentUserProfile from './current-profile/CurrentUserProfile'
import PublicUserProfile from './public-profile/PublicUserProfile'
import UserAvatar from './user-avatar/UserAvatar'
import UserCover from './user-cover/UserCover'

const UserProfile = () => {
    const { user, login } = useContext(AuthContext)
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

        if (username == user?.username) {
            setUserProfile(user)
            setLoading(false)
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
        <div className='w-11/12 max-w-screen-md mx-auto my-4 relative bg-white rounded-md shadow-md'>
            <UserCover userProfile={userProfile}
                setUserProfile={data => setUserProfile(data)} />
            <div className='p-8'>
                <UserAvatar userProfile={userProfile}
                    setUserProfile={data => setUserProfile(data)} />
            </div>
        </div>
    // user?.username == username ?
    //     <CurrentUserProfile userProfile={userProfile} /> :
    //     <PublicUserProfile userProfile={userProfile} />
}

export default UserProfile