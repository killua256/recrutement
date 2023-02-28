import { AuthContext } from '@contexts/index'
import userService from '@modules/user/services/user.service'
import { AppImage } from '@shared/components'
import { User } from '@shared/types'
import { getFile } from '@utils/fileHandler'
import { toastError, toastLoading, toastSuccess } from '@utils/toast'
import React, { useContext, useRef } from 'react'
import { FaCamera } from 'react-icons/fa'

interface UserCoverProps {
    userProfile: User | null
    setUserProfile: (data: User) => void
}

const UserCover = ({
    userProfile,
    setUserProfile
}: UserCoverProps) => {
    const { user, login } = useContext(AuthContext)
    const coverRef = useRef<any>()


    const coverChange = async (e: any) => {
        toastLoading()
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
    return (
        <>
            {
                user?.username == userProfile?.username ? (
                    <>
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
                    </>
                ) : null
            }
            <AppImage src={getFile(userProfile?.cover!)}
                type="COVER" alt="cover" className="w-full max-h-48 rounded-t-md" />
        </>
    )
}

export default UserCover