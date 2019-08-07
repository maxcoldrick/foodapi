class Food < ApplicationRecord
  validates :dish, presence: true
  validates :measurement, presence: true
end
