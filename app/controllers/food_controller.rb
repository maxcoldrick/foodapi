class FoodController < ApplicationController
  def index
    food = Food.order('created_at DESC')
    render json: {status: 'SUCCESS', message: 'Loaded meal details', data:food},status:ok
  end
end    
