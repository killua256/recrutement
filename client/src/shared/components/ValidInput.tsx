import React from 'react'
import { FieldError, UseFormRegisterReturn } from 'react-hook-form'

interface ValidInputProps {
    register: UseFormRegisterReturn
    errors: FieldError | undefined
    type?: string
}

const ValidInput = ({
    register,
    errors,
    type = "text"
}: ValidInputProps) => {
    return (
        <>
            <input type={type} {...register}
                className={`w-full p-2 border rounded mt-1 focus:border-primary-300 focus:outline-none focus:ring-1 
                    ${errors ? 'border-secondary-600' : 'border-gray-300'}`} />
            {
                errors ?
                    <div className="text-secondary-600">
                        {errors?.message}
                    </div>
                    : null
            }
        </>
    )
}

export default ValidInput