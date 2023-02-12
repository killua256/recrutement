import { Config } from "@config/Config"
import { Document } from "@shared/types"

export const getFile = (file: Document) => {
    return  `${Config.getConfig().apiUrl}docs/${file?.id}`
}

export const streamFile = (name: string) => {
    return  `${Config.getConfig().apiUrl}file/stream/${name}`
}

export const getFileApi = (res: string, name: string) => {
    return  `file/${res}/${name}`
}
