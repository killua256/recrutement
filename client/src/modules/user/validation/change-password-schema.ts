import * as Yup from 'yup';
import i18next from "i18next";


const changePasswordSchema = Yup.object().shape({
    currentPassword: Yup.string()
        .required(i18next.t('validation.required', {
            field: i18next.t('currentPassword')
        })!),
    newPassword: Yup.string()
        .required(i18next.t('validation.required', {
            field: i18next.t('newPassword')
        })!)
        .matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
            i18next.t('validation.password.pattern')!),
    confirmPassword: Yup.string()
        .required(i18next.t('validation.required', {
            field: i18next.t('confirmPassword')
        })!)
        .oneOf([Yup.ref('newPassword'), null],
            i18next.t('validation.password.match')!)

});

export default changePasswordSchema