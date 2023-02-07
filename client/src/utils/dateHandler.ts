import moment from "moment"

export const formateDate = (date: string) => {
    return date && date.toString().trim() !== "" ?
        moment(date).format("DD/MM/YYYY") : ""
}

export const formateDateTime = (date: string) => {
    return date && date.toString().trim() !== "" ?
        moment(date).format("DD/MM/YYYY HH:mm") : ""
}

export const formateReadableDateTime = (date: string) => {
    return date && date.toString().trim() !== "" ?
        moment(date).format('MMMM Do YYYY, h:mm a') : ""
}

export const fromNow = (date: string) => {
    return date && date.toString().trim() !== "" ?
        moment(date).fromNow() : ""
}

export const formatDateToInput = (date: string) => {
    return date && date.toString().trim() !== "" ?
        moment(date).format('YYYY-MM-DD') : ""
}