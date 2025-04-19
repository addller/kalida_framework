class Language{

    static setCodLang(codLang, redirectTo){
        localStorage.setItem('cod_lang', codLang)
        redirectTo && redirect(redirectTo)
    }

    static defineLang(){
        let codLang =  localStorage.getItem('cod_lang') || "PT_BR",
            registredLangs = ['PT_BR', 'EN_US'];

        keysOf(LANG).forEach(key =>{ 
            registredLangs.forEach(lang => {
                if(!LANG[key][lang])
                    throw `BASE_LANG incomplet for key ${key}, lang ${lang}`
            })
            LANG[key] = LANG[key][codLang]
        })
    }
}

const LANG = {
    cod_lang:{
        PT_BR:'PT_BR',
        EN_US:'EN_US'
    },
    yes:{
        PT_BR:'Sim',
        EN_US:'Yes'
    },
    not:{
        PT_BR:'Não',
        EN_US:'Not'
    },
    processing_request:{
        PT_BR:'Processando a requisição',
        EN_US:'Processing the request'
    },
    user_name:{
        PT_BR:'Nome de usuário',
        EN_US:'User name'
    },
    password:{
        PT_BR:'Senha',
        EN_US:'Password'
    },
    sign_in:{
        PT_BR:'Entrar',
        EN_US:'Sign in'
    },
    sign_out:{
        PT_BR:'Sair',
        EN_US:'Sign out'
    },
    email:{
        PT_BR:'Email',
        EN_US:'Email'
    },
    change_email:{
        PT_BR:'Mudar email',
        EN_US:'Change email'
    },
    changePassword:{
        PT_BR:'Mudar senha',
        EN_US:'Change password'
    },
    new_password:{
        PT_BR:'Nova senha',
        EN_US:'New password'
    },
    password_updated:{
        PT_BR:'Senha atualizada',
        EN_US:'Password updated'
    },
    current_password:{
        PT_BR:'Senha atual',
        EN_US:'Current password'
    },
    invalid_current_password:{
        PT_BR:'Senha atual inválida',
        EN_US:'Invalid current password'
    },
    lang:{
        PT_BR:'Idioma',
        EN_US:'Language'
    },
    cancel:{
        PT_BR:'Cancelar',
        EN_US:'Cancel'
    },
    back:{
        PT_BR:'Voltar',
        EN_US:'Back'
    },
    add:{
        PT_BR:'Adicionar',
        EN_US:'Add'
    },
    update:{
        PT_BR:'Atualizar',
        EN_US:'Update'
    },
    sign_up:{
        PT_BR:'Registrar',
        EN_US:'Sign up'
    },
    edit_profile:{
        PT_BR:'Editar perfil',
        EN_US:'Edit profile'
    },
    recover_access:{
        PT_BR:'Recuperar Acesso',
        EN_US:'Recover Access'
    },
    search:{
        PT_BR:'Pesquisar',
        EN_US:'Search'
    },
    home:{
        PT_BR:'Início',
        EN_US:'Home'
    },
    information:{
        PT_BR:'Info',
        EN_US:'Info'
    },
    registration_performed:{
        PT_BR:'Registro realizado',
        EN_US:'Registration performed'
    },
    close:{
        PT_BR:'Fechar',
        EN_US:'Close'
    },
    search_results:{
        PT_BR:'Resultados da pesquisa',
        EN_US:'Search Results'
    },
    user_or_password_invalid:{
        PT_BR:'Nome de usuário ou senha inválido',
        EN_US:'User name or password invalid'
    },
    access_denied:{
        PT_BR:'Acesso negado',
        EN_US:'Access denied'
    },
    summary:{
        PT_BR:'Sumário',
        EN_US:'Summary'
    },
    users:{
        PT_BR:'Usuários',
        EN_US:'Users'
    },
    login:{
        PT_BR:'Login',
        EN_US:'Login'
    },
    technologies:{
        PT_BR:'Tecnologias',
        EN_US:'Tecnologies'
    },
    choose_all:{
        PT_BR:'Escolher todos',
        EN_US:'Choose all'
    },
    name:{
        PT_BR:'Nome',
        EN_US:'Name'
    },
    username:{
        PT_BR:'Nome de usuário',
        EN_US:'User name'
    },
    nickName:{
        PT_BR:'Apelido',
        EN_US:'Nick name'
    },
    edit:{
        PT_BR:'Editar',
        EN_US:'Edit'
    },
    delete:{
        PT_BR:'Excluir',
        EN_US:'Delete'
    },
    type:{
        PT_BR:'Tipo',
        EN_US:'Type'
    },
    description:{
        PT_BR:'Descrição',
        EN_US:'Description'
    },
    confirm_delete_user:{
        PT_BR:'Confirma a exclusão do usuario?',
        EN_US:'Confirm delete user?'
    },
    start_year:{
        PT_BR:'Ano de início',
        EN_US:'Start year'
    },
    user:{
        PT_BR:'Usuário',
        EN_US:'User'
    },
    technology:{
        PT_BR:'Tecnologia',
        EN_US:'Technology'
    },
    experiences:{
        PT_BR:'Experiências',
        EN_US:'Experiences'
    },
    edit_user:{
        PT_BR:'Editar usuário',
        EN_US:'Edit user'
    },
    user_saved:{
        PT_BR:'Usuário salvo',
        EN_US:'User saved'
    },
    nothing_changed:{
        PT_BR:'Não há alterações a serem salvas',
        EN_US:'Nothing changed'
    },
    waiting:{
        PT_BR:'Aguardando',
        EN_US:'Waiting'
    },
    include_experience:{
        PT_BR:'Incluir experiência',
        EN_US:'Include experience'
    },
    select_technology:{
        PT_BR:'Selecionar tecnologia',
        EN_US:'Select technology'
    },
    my_experiences:{
        PT_BR:'Minhas experiências',
        EN_US:'My experiences'
    },
    have_not_experiences:{
        PT_BR:'Você não possui experiência',
        EN_US:'You have not experience'
    },
    choose_technology:{
        PT_BR:'Escolha uma tecnologia',
        EN_US:'Choose a technology'
    },
    clients:{
        PT_BR:'Clientes',
        EN_US:'Clients'
    },
    edit_client:{
        PT_BR:'Editar cliente',
        EN_US:'Edit client'
    }
}