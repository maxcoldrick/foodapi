Rails.application.routes.draw do
  mount_devise_token_auth_for 'User', at: 'auth'
  resources :food, constraints: { subdomain: 'api'}
end
