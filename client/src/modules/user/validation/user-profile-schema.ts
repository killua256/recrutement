import * as Yup from 'yup';
import i18next from "i18next";


const userProfileSchema = Yup.object().shape({
    email: Yup.string().email()
        .required(i18next.t('validation.required', {
            field: i18next.t('email')
        })!),
    username: Yup.string()
        .required(i18next.t('validation.required', {
            field: i18next.t('username')
        })!),
    firstname: Yup.string()
        .required(i18next.t('validation.required', {
            field: i18next.t('firstname')
        })!),
    lastname: Yup.string()
        .required(i18next.t('validation.required', {
            field: i18next.t('lastname')
        })!),
    mfaenabled: Yup.boolean(),

});

export default userProfileSchema