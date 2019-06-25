class FoodController < ApplicationController
  def index
    meal = Food.order('id DESC')
    render json: {status: 'SUCCESS', message: 'Loaded meal details', data:meal},status: :ok
  end

  def show
    meal = Food.find(params[:id])
    render json: {status: 'SUCCESS', message: 'Loaded meal', data:meal},status: :ok
  end

  def create
    meal = Food.new(meal_params)

    if meal.save
      render json: {status: 'SUCCESS', message: 'Saved meal', data:article},status: :ok
    else
      render json: {status: 'ERROR', message: 'Not saved', data:article.errors},status: :unprocessable_entity
    end
  end

  def meal_params
    params.permit(:dish, :measurement)
  end
end
