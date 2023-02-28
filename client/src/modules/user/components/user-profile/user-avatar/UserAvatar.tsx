import { AuthContext } from '@contexts/index'
import userService from '@modules/user/services/user.service'
import { Avatar } from '@shared/components'
import { User } from '@shared/types'
import { getFile } from '@utils/fileHandler'
import { toastError, toastLoading, toastSuccess } from '@utils/toast'
import React, { useContext, useRef } from 'react'
import { FaCamera } from 'react-icons/fa'

interface UserAvatarProps {
    userProfile: User | null
    setUserProfile: (data: User) => void
}

const UserAvatar = ({
    userProfile,
    setUserProfile
}: UserAvatarProps) => {
    const { user, login } = useContext(AuthContext)
    const avatarRef = useRef<any>()

    const avatarChange = async (e: any) => {
        toastLoading()
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

    return (
        <div className='relative w-max'>
            <Avatar user className="h-24 w-24 -mt-20 md:h-32 md:w-32 md:-mt-24 z-10 relative border-4 border-white border-solid rounded-full"
                src={getFile(userProfile?.avatar!)}
                alt={userProfile?.displayName} />
            {
                user?.username == userProfile?.username ? (
                    <>
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
                    </>
                ) : null
            }
            <h4 className='text-xl md:text-2xl font-semibold'>
                {userProfile?.displayName}
            </h4>
        </div>
    )
}

export default UserAvatar