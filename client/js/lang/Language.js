class Language{

    static setCodLang(codLang, redirectTo){
        codLang && localStorage.setItem('cod_lang', codLang)
        redirectTo && redirect(redirectTo)
    }

    static defineLang(){
        let cod_lang = localStorage.getItem('cod_lang') || "pt-BR";

        keysOf(LANG).forEach(key => {
            [LANG_PT_BR, LANG_EN_US]
                .forEach(lang => {
                    if(!(key in lang)) console.log(`Key[${key}] not found for lang ${lang.cod_lang}`)
                })
        })
        
        LANG = {
            "pt-BR":LANG_PT_BR,
            "en-US":LANG_EN_US
        }[cod_lang]

    }


}

var LANG = {
    cod_lang: 'pt-BR',
    yes: "Sim",
    not: "Não",
    processing_request:"Processando a requisição",
    user_name:"Nome de usuário",
    password:"Senha",
    sign_in:"Entrar",
    sign_up:"Registrar",
    sign_out:"Sair",
    email:"Email",
    change_email:'Mudar email',
    changePassword:"Mudar senha",
    new_password:"Nova senha",
    password_updated:"Senha atualizada",
    current_password:"Senha Atual",
    invalid_current_password:"Senha atual inválida",
    lang:"Idioma",
    cancel:"Cancelar",
    back:'Voltar',
    add:"Adicionar",
    update:"Atualizar",
    sign_up:"Registrar",
    edit_profile:"Editar perfil",
    recover_access:"Recuperar Acesso",
    search:"Pesquisar",
    edit_profile:"Editar perfil",
    home:"Início",
    information:"Info",
    registration_performed:"Registro realizado",
    close:"Fechar",
    search_results:"Resultados da pesquisa",
    user_or_password_invalid:"Nome de usuário ou senha inválido",
    access_denied:"Acesso negado",
    summary:"Sumário",
    users:"Usuários",
    login:"Login",
    technologies:"Tecnologias",
    choose_all:"Escolher todos",
    name: 'Nome',
    username: "Nome de usuário",
    nickName: "Apelido",
    edit: "Editar",
    delete: "Excluir",
    type:"Tipo",
    description:"Descrição",
    confirm_delete_user:"Confirma a exclusão do usuario?",
    start_year:"Ano de início",
    user:"Usuário",
    technology:"Tecnologia",
    experiences:"Experiências",
    edit_user:"Editar usuário",
    user_saved:"Usuário salvo",
    nothing_changed:"Não há alterações a serem salvas",
    waiting: "Aguardando",
    include_experience:"Incluir experiência",
    select_technology:"Selecionar tecnologia",
    my_experiences:"Minhas experiências",
    have_not_experiences:"Você não possui experiência",
    choose_technology:"Escolha uma tecnologia",
    clients:"Clientes",
    edit_client:"Editar cliente"
};

const LANG_PT_BR = LANG;

var LANG_EN_US = {
    cod_lang: 'en-US',
    yes: "Yes",
    not: "Not",
    processing_request:'Processing the request',
    user_name:"User name",
    password:"Password",
    sign_in:"Sign in",
    sign_up:"Sign up",
    edit_profile:'Edit profile',
    sign_out:"Sign out",
    email:"Email",
    change_email:"Change email",
    changePassword:"Change password",
    new_password:"New password",
    password_updated:"Password updated",
    current_password:"Current password",
    invalid_current_password:"Invalid current password",
    lang:"Language",
    cancel:"Cancel",
    back:'Back',
    add:"Add",
    update:"Update",
    sign_up:"Sign up",
    recover_access:"Recover Access",
    search:"Search",
    edit_profile:"Edit profile",
    home:"Home",
    information:"Info",
    registration_performed:'Registration performed',
    close:"Close",
    search_results:"Search Results",
    user_or_password_invalid:"User name or password invalid",
    access_denied:"Access denied",
    summary:"Summary",
    users:"Users",
    login:"Login",
    technologies:"Tecnologies",
    choose_all:"Escolher todos",
    name: "Name",
    username: "User name",
    nickName: "Nick name",
    edit: "Edit",
    delete: "Delete",
    type:"Type",
    description:"Description",
    confirm_delete_user:"Confirm delete user?",
    start_year:"Start year",
    user:"User",
    technology:"Technology",
    experiences:"Experiences",
    edit_user:"Edit user",
    user_saved:"User saved",
    nothing_changed:"Nothing changed",
    waiting:"Waiting",
    include_experience:"Include experience",
    select_technology:"Select technology",
    my_experiences:"My experiences",
    have_not_experiences:"You have not experience",
    choose_technology:"Choose a technology",
    clients:"Clients",
    edit_client:"Edit client"
};


