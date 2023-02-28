import { Switch } from '@headlessui/react'
import React, { Fragment } from 'react'
import { FieldError, UseFormRegisterReturn } from 'react-hook-form'

interface ToggleBtnProps {
  enabled: boolean
  setEnabled: any
  label?: string
}

const ToggleBtn = ({
  enabled,
  setEnabled,
  label
}: ToggleBtnProps) => {

  return (
    <div>
      <Switch checked={enabled} onChange={setEnabled} as={Fragment}>
        {({ checked }) => (
          <button
            className={`${checked ? 'bg-primary-600' : 'bg-gray-200'
              } relative inline-flex h-6 w-11 items-center rounded-full`}
          >
            <span className="sr-only"> {label} </span>
            <span
              className={`${checked ? 'translate-x-6' : 'translate-x-1'
                } inline-block h-4 w-4 transform rounded-full bg-white transition`}
            />
          </button>
        )}
      </Switch>
      {label ? <span className='mx-2'> {label} </span> : null}
    </div>
  )
}

export default ToggleBtn