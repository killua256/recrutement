import toast, { Toast } from "react-hot-toast";
import { AiOutlineWarning } from 'react-icons/ai'

const toastOptions:
    Partial<Pick<Toast, "id" | "icon" | "duration" | "ariaProps" | "className" | "style" | "position" | "iconTheme">> | undefined
    = {
    duration: 3500,
    position: 'top-center',
    style: {},
    className: '',
    ariaProps: {
        role: 'status',
        'aria-live': 'polite',
    }
}

const TOAST_ID = "my-toast"

export const toastNotif = (element: any) => {
    toast.custom((t) => (
        element
    ), {
        ...toastOptions
    })
}

export const toastCustom = (icon: any, text: string) => {
    toast.dismiss()
    toast.custom((
        <div className="custom-toast">
            <div className="custom-toast-icon">
                {icon}
            </div>
            <div className="custom-toast-text">
                {text}
            </div>
        </div>
    ), {
        ...toastOptions,
        id: TOAST_ID
    })
}

export const showToast = (text: string) => {
    toast.dismiss()
    toast(text.toString(), toastOptions)
}

export const toastError = (text: string) => {
    toast.error(text.toString(), {
        ...toastOptions,
        id: TOAST_ID
    });
}

export const toastWarning = (text: string) => {
    toast(text, {
        ...toastOptions,
        icon: <AiOutlineWarning className="text-orange-400"/>
    })
}

export const toastSuccess = (text: string) => {
    toast.success(text.toString(), {
        ...toastOptions,
        id: TOAST_ID
    });
}

export const toastLoading = () => {
    toast.loading("Loading ...", {
        id: TOAST_ID
    });
}
