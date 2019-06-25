class CreateFoods < ActiveRecord::Migration[5.2]
  def change
    create_table :foods do |t|
      t.text :dish
      t.text :measurement
    end
  end
end
