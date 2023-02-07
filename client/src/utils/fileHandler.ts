import { Config } from "@config/Config"

export const getFile = (src: string, name: string) => {
    return  `${Config.getConfig().apiUrl}file/${src}/${name}`
}

export const streamFile = (name: string) => {
    return  `${Config.getConfig().apiUrl}file/stream/${name}`
}

export const getFileApi = (res: string, name: string) => {
    return  `file/${res}/${name}`
}
