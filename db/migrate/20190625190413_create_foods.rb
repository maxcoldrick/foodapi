class CreateFoods < ActiveRecord::Migration[5.2]
  def change
    create_table :foods do |t|
      t.text :dish
      t.text :measurement
      t.text :description
      t.text :ingredient
      t.text :measurement
      t.text :spice
    end
  end
end
