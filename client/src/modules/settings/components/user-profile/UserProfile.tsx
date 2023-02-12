import { AuthContext } from '@contexts/index'
import userService from '@modules/settings/services/user.service'
import { AppImage, Avatar, PageLoading } from '@shared/components'
import { User } from '@shared/types'
import { getFile } from '@utils/fileHandler'
import { toastError, toastSuccess } from '@utils/toast'
import React, { useContext, useEffect, useRef, useState } from 'react'
import { FaCamera } from 'react-icons/fa'
import { useNavigate, useParams } from 'react-router-dom'
import CurrentUserProfile from './current-profile/CurrentUserProfile'
import PublicUserProfile from './public-profile/PublicUserProfile'

const UserProfile = () => {
    const { user, login } = useContext(AuthContext)
    const { username } = useParams()
    const navigate = useNavigate()
    const avatarRef = useRef<any>()
    const coverRef = useRef<any>()

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

    const avatarChange = async (e: any) => {
        let formData = new FormData()
        formData.append('avatar', e.target.files[0])
        const { response, success } = await userService.updateAvatar(formData);
        if (success && response) {
            login(response)
            setUserProfile(response)
            toastSuccess("Avatar updated successfully")
        } else {
            toastError("Updating avatar failed")
        }
    }

    const coverChange = async (e: any) => {
        let formData = new FormData()
        formData.append('cover', e.target.files[0])
        const { response, success } = await userService.updateCover(formData);
        if (success && response) {
            login(response)
            setUserProfile(response)
            toastSuccess("Cover updated successfully")
        } else {
            toastError("Updating cover failed")
        }
    }

    useEffect(() => {
        loadUserProfile()
    }, [])

    return loading ? <PageLoading /> :
        <div className='w-11/12 max-w-screen-md mx-auto my-4 relative bg-white rounded-md shadow-md'>
            <input
                ref={coverRef}
                type="file"
                onChange={coverChange}
                hidden
                accept='image/*'
            />
            <button onClick={() => coverRef.current.click()}
                className='absolute p-1 bg-gray-200 rounded-full right-4 top-4 z-10'>
                <FaCamera className='text-gray-500' size={18} />
            </button>
            <AppImage src={getFile(userProfile?.cover!)}
                type="COVER" alt="cover" className="w-full max-h-48 rounded-t-md" />
            <div className='p-8'>
                <div className='relative w-max'>
                    <Avatar user className="h-24 w-24 -mt-20 md:h-32 md:w-32 md:-mt-24 z-10 relative border-4 border-white border-solid rounded-full"
                        src={getFile(userProfile?.avatar!)}
                        alt={userProfile?.displayName} />
                    <input
                        ref={avatarRef}
                        type="file"
                        onChange={avatarChange}
                        hidden
                        accept='image/*'
                    />
                    <button onClick={() => avatarRef.current.click()}
                        className='absolute p-1 bg-gray-200 rounded-full right-6 md:right-4 bottom-10 md:bottom-12 z-10'>
                        <FaCamera className='text-gray-500' size={18} />
                    </button>
                    <h4 className='text-xl md:text-2xl font-semibold'>
                        {userProfile?.displayName}
                    </h4>
                </div>
            </div>
        </div>
    // user?.username == username ?
    //     <CurrentUserProfile userProfile={userProfile} /> :
    //     <PublicUserProfile userProfile={userProfile} />
}

export default UserProfile