import { Document } from "./document";

export type User = {
    id? : number
    displayName? : string
    email? : string
    firstname? : string
    lastname? :  string
    username? :  string
    avatar? : Document
    cover? : Document
    //role? : Role
    createdAt? : Date
}